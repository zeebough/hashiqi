package com.ghtt.socialplatform.global;

import java.util.UUID;

public final class ServerProperties {
//  内部请求转发使用的ID，防止私有资源被外部访问
    public static final String INTERNAL_SERVER_REQUEST= UUID.randomUUID().toString();
//    签发token时使用的ID
    public static final String TOKEN_ISSUER= UUID.randomUUID().toString();

    public static final Long DEFAULT_PAGE_SIZE=20L;

    public static final String PASSWORD_SALT="f20a422e-baed-4113-8f2d-90b90f28b721";

}
