package com.lsuncar.securityhub.enumerated;

public enum SystemConfigKeys {

    ORGANIZATION_NAME( false, "Organization name", TypeDef.STRING),
    PROJECT_NAME(false, "Project name", TypeDef.STRING),
    PROJECT_LOGO(false, "Project logo Name", TypeDef.STRING),

    SMTP_USERNAME(true, "SMTP username", TypeDef.STRING),
    SMTP_PASSWORD (true, "SMTP password", TypeDef.STRING),
    SMTP_HOST ( false, "SMTP host", TypeDef.STRING),
    SMTP_PORT (false, "",TypeDef.INTEGER),
    SMTP_PROTOCOL(false, "SMTP,SMTPS", TypeDef.STRING ),  //usually "smtp"

    SMTP_AUTH_ENABLED(false, "SMPT Auth enabled", TypeDef.BOOOLEAN),
    SMTP_SSL_ENABLED(false, "SMTP SSL enabled", TypeDef.BOOOLEAN),
    SMTP_STARTTLS_ENABLED(false, "SMTP StartTLS enabled", TypeDef.BOOOLEAN),

    //google oauth2 config
    GOOGLE_CLIENT_ID(true, "Google client id", TypeDef.STRING),
    GOOGLE_CLIENT_SECRET(true, "Google client secret", TypeDef.STRING),

    //apple oauth2 config
    APPLE_CLIENT_ID(false, "Apple client id", TypeDef.STRING),
    APPLE_CLIENT_SECRET(true, "Apple client secret", TypeDef.STRING),
    APPLE_TEAM_ID(false, "Apple team id", TypeDef.STRING),
    APPLE_KEY_ID(false, "Apple key id", TypeDef.STRING),
    APPLE_PRIVATE_KEY_PATH(false, "Apple private key path", TypeDef.STRING),
    APPLE_REDIRECT_URI(false, "Apple redirect uri", TypeDef.STRING),


    ;
    private final boolean secret;
    private final String description;
    private final TypeDef type;

    SystemConfigKeys(boolean secret, String description, TypeDef type) {
        this.secret = secret;
        this.description = description;
        this.type = type;
    }

    public boolean isSecret() {
        return secret;
    }

    public String getDescription() {
        return description;
    }

    public TypeDef getType() {
        return type;
    }
}