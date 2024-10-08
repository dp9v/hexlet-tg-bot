package com.dp9v.hexlet_tg_bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {
    private static final String ID_FIELD = "id";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String IS_BOT_FIELD = "is_bot";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String USERNAME_FIELD = "username";
    private static final String LANGUAGE_CODE_FIELD = "language_code";
    private static final String CAN_JOIN_GROUPS_FIELD = "can_join_groups";
    private static final String CAN_READ_ALL_GROUP_MESSAGES_FIELD = "can_read_all_group_messages";
    private static final String SUPPORT_INLINE_QUERIES_FIELD = "supports_inline_queries";
    private static final String IS_PREMIUM_FIELD = "is_premium";
    private static final String ADDED_TO_ATTACHMENT_MENU_FIELD = "added_to_attachment_menu";
    private static final String CAN_CONNECT_TO_BUSINESS_FIELD = "can_connect_to_business";

    @JsonProperty(ID_FIELD)
    private Long id;
    /**
     * User‘s or bot’s first name
     */
    @JsonProperty(FIRST_NAME_FIELD)
    private String firstName;
    /**
     * True, if this user is a bot
     */
    @JsonProperty(IS_BOT_FIELD)
    private Boolean isBot;
    /**
     * Optional.
     * User‘s or bot’s last name
     */
    @JsonProperty(LAST_NAME_FIELD)
    private String lastName;
    /**
     * Optional.
     * User‘s or bot’s username
     */
    @JsonProperty(USERNAME_FIELD)
    private String userName;
    /**
     * Optional.
     * IETF language tag of the user's language
     */
    @JsonProperty(LANGUAGE_CODE_FIELD)
    private String languageCode;
    /**
     * Optional.
     * True, if the bot can be invited to groups. Returned only in getMe.
     */
    @JsonProperty(CAN_JOIN_GROUPS_FIELD)
    private Boolean canJoinGroups;
    /**
     * Optional.
     * True, if privacy mode is disabled for the bot. Returned only in getMe.
     */
    @JsonProperty(CAN_READ_ALL_GROUP_MESSAGES_FIELD)
    private Boolean canReadAllGroupMessages;
    /**
     * Optional.
     * True, if the bot supports inline queries. Returned only in getMe.
     */
    @JsonProperty(SUPPORT_INLINE_QUERIES_FIELD)
    private Boolean supportInlineQueries;
    /**
     * Optional.
     * True, if this user is a Telegram Premium user
     */
    @JsonProperty(IS_PREMIUM_FIELD)
    private Boolean isPremium;
    /**
     * Optional.
     * True, if this user added the bot to the attachment menu
     */
    @JsonProperty(ADDED_TO_ATTACHMENT_MENU_FIELD)
    private Boolean addedToAttachmentMenu;
    /**
     * Optional.
     * True, if the bot can be connected to a Telegram Business account to receive its messages.
     * Returned only in getMe.
     */
    @JsonProperty(CAN_CONNECT_TO_BUSINESS_FIELD)
    private Boolean canConnectToBusiness;

}
