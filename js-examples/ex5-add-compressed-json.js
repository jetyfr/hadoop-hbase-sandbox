const hbase = require('hbase');
const zlib = require('zlib');
const util = require('util');

const compress = util.promisify(zlib.deflate);
const decompress = util.promisify(zlib.inflate);

const client = hbase({
    host: 'master-bd1.inv.usc.es',
    port: 5080
});

const tableName = 'compressed_products';
const columnFamily = 'data';

const complexProduct = {
    id: 'prod001',
    name: 'Pro Max Laptop',
    description: 'Premium professional laptop with high-end specifications',
    price: 1999.99,
    specifications: {
        processor: {
            brand: 'Intel',
            model: 'i9-12900K',
            cores: 16,
            frequency: '5.2GHz'
        },
        memory: {
            type: 'DDR5',
            capacity: '32GB',
            frequency: '4800MHz',
            modules: [
                { slot: 1, capacity: '16GB' },
                { slot: 2, capacity: '16GB' }
            ]
        },
        storage: [
            { type: 'NVMe SSD', capacity: '2TB', speed: '7000MB/s' },
            { type: 'SATA SSD', capacity: '2TB', speed: '560MB/s' }
        ],
        gpu: {
            brand: 'NVIDIA',
            model: 'RTX 3080',
            memory: '16GB GDDR6X'
        }
    }
};

async function saveCompressed(rowKey, data) {
    try {
        const jsonString = JSON.stringify(data);
        const buffer = Buffer.from(jsonString, 'utf8');
        const compressed = await compress(buffer);
        
        return new Promise((resolve, reject) => {
            client.table(tableName)
                .row(rowKey)
                .put(`${columnFamily}:json`, compressed.toString('base64'), (err, success) => {
                    if (err) reject(err);
                    else resolve(success);
                });
        });
    } catch (error) {
        console.error('Error compressing/saving:', error);
        throw error;
    }
}

async function readAndDecompress(rowKey) {
    try {
        return new Promise((resolve, reject) => {
            client.table(tableName)
                .row(rowKey)
                .get(`${columnFamily}:json`, async (err, cells) => {
                    if (err) {
                        reject(err);
                        return;
                    }
                    
                    if (!cells || cells.length === 0) {
                        console.log('No data found for key:', rowKey);
                        resolve(null);
                        return;
                    }

                    try {
                        const value = cells[0]['$'];
                        
                        if (!value) {
                            throw new Error('No value found in response');
                        }

                        const compressed = Buffer.from(value, 'base64');
                        const decompressed = await decompress(compressed);
                        const data = JSON.parse(decompressed.toString('utf8'));
                        resolve(data);
                    } catch (error) {
                        console.error('Error processing data:', error);
                        reject(error);
                    }
                });
        });
    } catch (error) {
        console.error('Error reading/decompressing:', error);
        throw error;
    }
}

async function runExample() {
    try {
        await new Promise((resolve, reject) => {
            client.table(tableName).create(columnFamily, (err, success) => {
                if (err && !err.toString().includes('TableExistsException')) {
                    reject(err);
                } else {
                    resolve(success);
                }
            });
        });

        console.log('✅ Table created/verified');
        await saveCompressed('laptop1', complexProduct);
        console.log('✅ Data saved and compressed');

        const retrievedData = await readAndDecompress('laptop1');
        console.log('\n📋 Retrieved and decompressed data:');
        console.log(JSON.stringify(retrievedData, null, 2));

    } catch (error) {
        console.error('❌ Error:', error);
    }
}

runExample();
