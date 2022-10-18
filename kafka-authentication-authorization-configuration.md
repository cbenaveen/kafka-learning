## Zookeeper Authentication Configuration

### Configure `conf/zoo.cfg`
```properties
authProvider.1=org.apache.zookeeper.server.auth.SASLAuthenticationProvider
requireClientAuthScheme=SASL
jaasLoginRenew=3600000
```

### Create and Configure `conf/zookeeper-server.jaas`
```properties
Server {
       org.apache.zookeeper.server.auth.DigestLoginModule required
       user_admin="adminsecret"
       user_operator="operatorsecrete";
};
```

### Create and Configure `conf/java.env`
Replace `ZK_HOME` with correct zookpeer installation directory location
```properties
SERVER_JVMFLAGS="-Djava.security.auth.login.config=<ZK_HOME>conf/zookeeper-server.jaas"
```

### Start the Zookeeper
```shell
bin/zkServer.sh start-foreground
```

## Kafka Authentication Configuration

### Create `jaas-kafka-server.conf`
```properties
Client {
       org.apache.zookeeper.server.auth.DigestLoginModule required
       username="admin"
       password="adminsecret";
};

KafkaServer {
    org.apache.kafka.common.security.plain.PlainLoginModule required
    username="admin"
    password="admin"
    user_admin="admin"
    user_operator="operator"
    user_producer="producer";
};
```

### Enable SASL in kafka `server.properties`
```properties
authorizer.class.name=kafka.security.authorizer.AclAuthorizer
listeners=SASL_PLAINTEXT://:9092
security.inter.broker.protocol= SASL_PLAINTEXT
sasl.mechanism.inter.broker.protocol=PLAIN
sasl.enabled.mechanisms=PLAIN
super.users=User:admin
```

### Start Kafka broker
```properties
export KAFKA_OPTS="-Djava.security.auth.login.config=<KAFKA_HOME>/config/jaas-kafka-server.conf"
bin/kafka-server-start.sh config/server.properties
```

### Create kafka authentication client configuration called `kafka-client.properties`
```properties
security.protocol=SASL_PLAINTEXT
sasl.mechanism=PLAIN
bootstrap.servers=localhost:9092
sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required \
        username="producer" \
        password="producer";
```

### Use the `kafka-client.properties` in `kafka-topic.sh` cli
```shell
bin/kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --create --topic python-sample-2 \
    --replication-factor 1 --partitions 5 \
    --command-config producer.properties
```
