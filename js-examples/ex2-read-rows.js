const hbase = require("hbase");

const client = hbase({ host: "master-bd1.inv.usc.es", port: 5080 });

const tableName = "my_table";

function readAllRows() {
  client.table(tableName).scan({ maxVersions: 1 }, function (err, rows) {
    if (err) {
      console.error("❌ Error al leer la tabla:", err);
      return;
    }
    console.log("Filas en la tabla:" + tableName);
    rows.forEach(function (row) {
      console.log(`Fila: ${row.key}`);
      if (row.$) {
        const cells = Array.isArray(row.$) ? row.$ : [row.$];
        console.log(cells);
      } else if (row.columns) {
        Object.keys(row.columns).forEach(function (col) {
          console.log(`   ${col}: ${row.columns[col].value}`);
        });
      } else {
        console.log("   Sin datos en la fila");
      }
    });
  });
}

readAllRows();
