package com.bookstore.common.util;

public class Uri {

    /* Auth */
    public static final String LOGIN = "/api/auth/login";
    public static final String SIGNUP = "/api/auth/signup";

    public static final String REFRESH = "/api/auth/refresh";

    /* User */
    public static final String USER = "/api/user";
    public static final String USER_ORDER = "/api/user/order";
    public static final String USERS_REVIEWS = "/api/users/reviews";

    /* Order */
    public static final String ORDER = "/api/order";
    public static final String ORDER_ITEM = "/api/order/item";
    public static final String ORDER_CONFIRM = "/api/order/confirm";
    /* Book */
    public static final String BOOK = "/api/book";
    public static final String BOOK_DETAIL = "/api/book/detail/{id}";
    public static final String BOOK_SHOP = "/api/book/shop/{id}";
    public static final String BOOK_PAGE = "/api/book/page";
    public static final String BOOKS_FILTER = "/api/book/filter";

    public static final String BOOKS_REVIEWS = "/api/book/review/page";
    public static final String BOOK_RATE = "/api/book/rate/count";

    /* Shop */
    public static final String SHOP = "/api/shop";
    public static final String SHOP_BOOK = "/api/shop/book";
    public static final String SHOP_DETAIL = "/api/shop/detail";
    public static final String SHOP_ORDER = "/api/shop/order";

    /* Category */
    public static final String CATEGORY = "/api/category";
}
