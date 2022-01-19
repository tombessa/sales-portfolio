package github.com.tombessa.salesportfolio.enums;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum UserAccessStatusEnum {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    UserAccessStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static UserAccessStatusEnum getStringAsUserAccessStatusEnum(String value) {
        AtomicReference<UserAccessStatusEnum> ret = null;
        Arrays.stream(UserAccessStatusEnum.values()).forEach(status -> {
            if(value.equals(status.getStatus())) ret.set(status);
        });
        return ret.get();
    }
}
