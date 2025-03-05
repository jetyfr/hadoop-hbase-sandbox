const hbase = require("hbase");

const client = hbase({
  host: "master-bd1.inv.usc.es",
  port: 5080,
});

client.tables((err, tables) => {
  if (err) {
    console.error("Error al obtener tablas:", err);
  } else {
    console.log("Tablas en HBase:", tables);
  }
});
