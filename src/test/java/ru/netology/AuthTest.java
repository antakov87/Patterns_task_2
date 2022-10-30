package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.DataGenerator.getNewUser;
import static ru.netology.DataGenerator.getRegisteredUser;

class AuthTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    void shouldUserActive() {
        DataGenerator.RegistrationDto registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldBe(visible).shouldHave(exactText("Личный кабинет"));
    }

    @Test
    void shouldUserBlocked() {
        DataGenerator.RegistrationDto blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void shouldUserActiveLogin() {
        DataGenerator.RegistrationDto newUser = getNewUser("active");
        $("[data-test-id='login'] input").setValue(newUser.getLogin());
        $("[data-test-id='password'] input").setValue(newUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldUserActiveLoginNot() {
        DataGenerator.RegistrationDto registeredUser = getRegisteredUser("active");
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='login'] .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldUserActivePasswordNot() {
        DataGenerator.RegistrationDto registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='password'] .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }
}