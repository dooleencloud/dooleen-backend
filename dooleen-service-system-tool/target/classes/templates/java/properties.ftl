server.port=8008
##spring.profile.active = dev

spring.cloud.nacos.config.server-addr=nacos.dooleen.com:8848
spring.application.name=${sysToolTablesEntity.projectName}
spring.cloud.nacos.config.prefix=dooleen-config
logging.level.${sysToolTablesEntity.packageName}.*=debug 
logging.file.max-history=10
logging.file.max-size=10MB
debug=true