package org.codinjutsu.tools.jenkins.view.validator;

import javax.swing.JComponent;

public enum ValidatorTypeEnum {

    STRICT_POSITIVE_INTEGER(new StrictPositiveIntegerValidator()),
    POSITIVE_INTEGER(new PositiveIntegerValidator()),
    URL(new UrlValidator()),
    NOTNULL(new NotNullValidator());

    private final UIValidator<? extends JComponent> validator;


    ValidatorTypeEnum(UIValidator<? extends JComponent> validator) {
        this.validator = validator;
    }


    public UIValidator<? extends JComponent> getValidator() {
        return validator;
    }
}
