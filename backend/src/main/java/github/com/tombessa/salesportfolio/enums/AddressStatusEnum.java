package github.com.tombessa.salesportfolio.enums;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum AddressStatusEnum {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    AddressStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static AddressStatusEnum getStringAsAddressStatusEnum(String value) {
        AtomicReference<AddressStatusEnum> ret = null;
        Arrays.stream(AddressStatusEnum.values()).forEach(status -> {
            if(value.equals(status.getStatus())) ret.set(status);
        });
        return ret.get();
    }
}
