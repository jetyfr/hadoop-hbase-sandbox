const hbase = require("hbase");

const client = hbase({
  host: "master-bd1.inv.usc.es",
  port: 5080,
});

const tableName = "my_table";
const columnFamily = "my_column_family";

client.table(tableName).create(columnFamily, function (err, success) {
  if (err) {
    console.error("❌ Error creating table:", err);
  } else {
    console.log(`✅ Table '${tableName}' created successfully`);
  }

  client
    .table(tableName)
    .row("row1")
    .put(`${columnFamily}:name`, "Alice", function (err, success) {
      if (err) {
        console.error("❌ Error inserting row1:name:", err);
        return;
      }
      console.log("✅ Inserted row1:name");

      client
        .table(tableName)
        .row("row1")
        .put(`${columnFamily}:age`, "30", function (err, success) {
          if (err) {
            console.error("❌ Error inserting row1:age:", err);
            return;
          }
          console.log("✅ Inserted row1:age");

          client
            .table(tableName)
            .row("row2")
            .put(`${columnFamily}:name`, "Bob", function (err, success) {
              if (err) {
                console.error("❌ Error inserting row2:name:", err);
                return;
              }
              console.log("✅ Inserted row2:name");

              client
                .table(tableName)
                .row("row2")
                .put(`${columnFamily}:age`, "25", function (err, success) {
                  if (err) {
                    console.error("❌ Error inserting row2:age:", err);
                    return;
                  }
                  console.log("✅ Inserted row2:age");
                  readAllRows();
                });
            });
        });
    });
});

function readAllRows() {
  client.table(tableName).scan({ maxVersions: 1 }, function (err, rows) {
    if (err) {
      console.error("❌ Error reading table:", err);
      return;
    }
    console.log("✅ Retrieved rows:");
    rows.forEach(function (row) {
      console.log(`Row: ${row.key}`);
      if (row.$) {
        const cells = Array.isArray(row.$) ? row.$ : [row.$];
        console.log(cells);
      } else if (row.columns) {
        Object.keys(row.columns).forEach(function (col) {
          console.log(`   ${col}: ${row.columns[col].value}`);
        });
      } else {
        console.log("   No data in row");
      }
    });
  });
}
