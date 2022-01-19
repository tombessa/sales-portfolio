package github.com.tombessa.salesportfolio.enums;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum RoleStatusEnum {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    RoleStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static RoleStatusEnum getStringAsRoleStatusEnum(String value) {
        AtomicReference<RoleStatusEnum> ret = null;
        Arrays.stream(RoleStatusEnum.values()).forEach(status -> {
            if(value.equals(status.getStatus())) ret.set(status);
        });
        return ret.get();
    }
}
