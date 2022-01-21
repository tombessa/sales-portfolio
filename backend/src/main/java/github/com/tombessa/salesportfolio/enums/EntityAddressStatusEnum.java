package github.com.tombessa.salesportfolio.enums;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum EntityAddressStatusEnum {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    EntityAddressStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static EntityAddressStatusEnum getStringAsEntityAddressStatusEnum(String value) {
        AtomicReference<EntityAddressStatusEnum> ret = null;
        Arrays.stream(EntityAddressStatusEnum.values()).forEach(status -> {
            if(value.equals(status.getStatus())) ret.set(status);
        });
        return ret.get();
    }
}
