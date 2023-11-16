package com.felipe.admin.catalogo.domain.exceptions;

import com.felipe.admin.catalogo.domain.validation.handler.Notification;

public class NotificationException extends DomainException {
    public NotificationException(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErros());
    }
}
