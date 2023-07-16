FROM apache/hadoop:3

COPY start-namenode.sh /
RUN sudo chmod a+x /start-namenode.sh

WORKDIR /data
RUN mkdir -p name storage timeline
RUN sudo chown -R hadoop /data

WORKDIR /opt/hadoop
RUN mkdir -p output samples archives
RUN sudo chown -R hadoop /opt/hadoop
