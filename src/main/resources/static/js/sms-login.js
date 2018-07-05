AccountKit_OnInteractive = function() {
    AccountKit.init({
        appId: "1529574253759244",
        state :"f82b070b-c1c0-4214-a4a3-7a1b98d4d132",
        version: "v1.1",
        fbAppEventsEnabled: true
    });
};

function loginCallback(response) {
    if (response.status === "PARTIALLY_AUTHENTICATED") {
        // Send code to server to exchange for access token
        document.getElementById("code").value = response.code;
        document.getElementById("csrf").value = response.state;
        document.getElementById("login_success").submit();
    } else if (response.status === "NOT_AUTHENTICATED") {
        // handle authentication failure
    } else if (response.status === "BAD_PARAMS") {
        // handle bad parameters
    }
}

function smsLogin() {
    var countryCode = document.getElementById("country_code").value;
    var phoneNumber = document.getElementById("phone_number").value;
    AccountKit.login(
        'PHONE',
        {countryCode: countryCode, phoneNumber: phoneNumber}, // will use default values if not specified
        loginCallback
    );
}
