FROM eclipse-temurin:8-jre-jammy

ENV HBASE_VERSION 2.5.5
ENV HBASE_URL https://downloads.apache.org/hbase/$HBASE_VERSION/hbase-$HBASE_VERSION-bin.tar.gz

# install
RUN set -x \
  && curl -fSL "$HBASE_URL" -o /tmp/hbase.tar.gz \
  && curl -fSL "$HBASE_URL.asc" -o /tmp/hbase.tar.gz.asc \
  && mkdir -p /opt/hbase/logs \
  && tar --strip-components=1 -xvf /tmp/hbase.tar.gz -C /opt/hbase \
  && rm -r -f /tmp/hbase.tar.gz*

# configuration
RUN ln -s /opt/hbase/conf /etc/hbase

ENV HBASE_HOME /opt/hbase
ENV HBASE_CONF_DIR /etc/hbase
ENV PATH $HBASE_HOME/bin/:$PATH

COPY hbase-site.xml /etc/hbase

# access
RUN groupadd --gid 1000 hbase
RUN useradd --uid 1000 hbase --gid 100 --home /opt/hbase
RUN chmod 755 /opt/hbase
RUN echo "hbase ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers
RUN chown hbase /opt

USER hbase