const hbase = require("hbase");

const client = hbase({
  host: "master-bd1.inv.usc.es",
  port: 5080,
});

const tableName = "products";
const columnFamily = "details";

const products = [
  {
    id: "prod001",
    data: {
      name: "Gaming Laptop",
      price: 999.99,
      specs: {
        ram: "16GB",
        processor: "Intel i7",
        storage: "512GB SSD",
      },
    },
  },
  {
    id: "prod002",
    data: {
      name: "Pro Smartphone",
      price: 699.99,
      specs: {
        ram: "8GB",
        processor: "Snapdragon 888",
        storage: "256GB",
      },
    },
  },
];

client.table(tableName).create(columnFamily, function (err, success) {
  if (err) {
    console.error("❌ Error creating table:", err);
    return;
  }
  console.log(`✅ Table '${tableName}' created successfully`);
  insertProducts(0);
});

function insertProducts(index) {
  if (index >= products.length) {
    console.log("✅ All products inserted");
    readProducts();
    return;
  }

  const product = products[index];
  client
    .table(tableName)
    .row(product.id)
    .put(`${columnFamily}:data`, JSON.stringify(product.data), function (err, success) {
      if (err) {
        console.error(`❌ Error inserting product ${product.id}:`, err);
        return;
      }
      console.log(`✅ Inserted product ${product.id}`);
      insertProducts(index + 1);
    });
}

function readProducts() {
  client.table(tableName).scan({ maxVersions: 1 }, function (err, rows) {
    if (err) {
      console.error("❌ Error reading products:", err);
      return;
    }

    console.log("\n📋 Retrieved products:");
    rows.forEach((row) => {
      console.log(`\nProduct ID: ${row.key}`);
      if (row.columns) {
        Object.keys(row.columns).forEach((col) => {
          const data = JSON.parse(row.columns[col].value);
          console.log("Data:", JSON.stringify(data, null, 2));
        });
      }
    });
  });
}
