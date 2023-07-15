#! /bin/bash

ls -l -a /opt/hadoop/dfs

if [ ! -n "$ENSURE_NAMENODE_CLUSTERID" ] || [ ! -n "$ENSURE_NAMENODE_DIR" ]; then
  echo "please set ENSURE_NAMENODE_CLUSTERID and ENSURE_NAMENODE_DIR"
  exit 1
fi

echo cluster id is $ENSURE_NAMENODE_CLUSTERID

if [ ! -d "$ENSURE_NAMENODE_DIR" ]; then
  echo "creating namenode dir $ENSURE_NAMENODE_DIR"
  mkdir -p $ENSURE_NAMENODE_DIR
fi

if [ ! "$(ls -A $ENSURE_NAMENODE_DIR)" ]; then
  echo "formatting namenode"
  hdfs namenode -format -force -clusterid $ENSURE_NAMENODE_CLUSTERID
  echo "namenode formatted"
fi

hdfs namenode