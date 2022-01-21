package github.com.tombessa.salesportfolio.enums;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum ClientStatusEnum {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    ClientStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static ClientStatusEnum getStringAsClientStatusEnum(String value) {
        AtomicReference<ClientStatusEnum> ret = null;
        Arrays.stream(ClientStatusEnum.values()).forEach(status -> {
            if(value.equals(status.getStatus())) ret.set(status);
        });
        return ret.get();
    }
}
