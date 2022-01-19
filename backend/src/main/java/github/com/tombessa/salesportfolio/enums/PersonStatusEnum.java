package github.com.tombessa.salesportfolio.enums;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum PersonStatusEnum {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    PersonStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static PersonStatusEnum getStringAsPersonStatusEnum(String value) {
        AtomicReference<PersonStatusEnum> ret = null;
        Arrays.stream(PersonStatusEnum.values()).forEach(status -> {
            if(value.equals(status.getStatus())) ret.set(status);
        });
        return ret.get();
    }
}
