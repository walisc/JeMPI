package org.jembi.jempi.monitor.operations.data;

import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jembi.jempi.monitor.BaseResponse;
import org.jembi.jempi.monitor.lib.LibRegistry;
import org.jembi.jempi.monitor.operations.BaseProcessor;
import org.jembi.jempi.monitor.lib.dal.IDAL;

import java.util.Objects;
import java.util.concurrent.Callable;

import static akka.http.javadsl.server.Directives.complete;


public class DataEndPointProcessor extends BaseProcessor {
    private static final Logger LOGGER = LogManager.getLogger(DataEndPointProcessor.class);
    public DataEndPointProcessor(LibRegistry libRegistry) {
        super(libRegistry);
    }

    private IDAL GetDAL(String dbType) throws ClassNotFoundException{
        if (dbType.equals("postgres")){
            return this.libRegistry.postgres;
        }
        else if (dbType.equals("dgraph")){
            return this.libRegistry.dGraph;
        }
        throw new ClassNotFoundException(String.format("Unknown dbType %s", dbType));
    }

    public Route deleteAll(String dbType, String tableName, Boolean force){
        if (this.libRegistry.runnerChecker.IsJeMPIRunning() && !force){
           return complete(StatusCodes.FORBIDDEN,
                   new BaseResponse("Cannot delete data whilst JeMPI is running. Please stop jempi services first (or append the url with /force) ", true),
                   JSON_MARSHALLER);
        }

        try {
            Callable<Boolean> runFunc;
            if (!Objects.equals(tableName, "__all")){
                runFunc = () -> this.GetDAL(dbType).deleteTableData(tableName);
            }
            else{
                runFunc = () -> this.GetDAL(dbType).deleteAllData();
            }

            if (runFunc.call()){
                return complete(StatusCodes.OK, new BaseResponse("Success", false), JSON_MARSHALLER);
            }
            return complete(StatusCodes.INTERNAL_SERVER_ERROR, new BaseResponse("Was unable to delete table data", true), JSON_MARSHALLER);

        } catch (Exception e){
            LOGGER.error("An error occurred whilst try to delete the data", e);
            return complete(StatusCodes.INTERNAL_SERVER_ERROR, new BaseResponse(String.format("An error occurred whilst try to delete the data. Error Message: %s", e.getMessage()), true), JSON_MARSHALLER);
        }

    }




}