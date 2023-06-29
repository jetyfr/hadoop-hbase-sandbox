FROM apache/hadoop:3

WORKDIR /opt/hadoop

RUN mkdir -p scripts samples dfs/name dfs/data yarn/timeline
