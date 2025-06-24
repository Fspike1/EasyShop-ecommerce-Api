let profileService;

class ProfileService {
    loadProfile() {
        const url = `${config.baseUrl}/profile`;
        const user = userService.getCurrentUser();

        if (!user || !user.token) {
            const data = { error: "You must be logged in to view your profile." };
            templateBuilder.append("error", data, "errors");
            return;
        }

        axios.get(url, {
            headers: {
                Authorization: `Bearer ${user.token}`
            }
        })
        .then(response => {
            templateBuilder.build("profile", response.data, "main");
        })
        .catch(error => {
            const data = {
                error: "Load profile under maintenance coming soon."
            };
            templateBuilder.append("error", data, "errors");
        });
    }

    updateProfile(profile) {
        const url = `${config.baseUrl}/profile`;
        const user = userService.getCurrentUser();

        if (!user || !user.token) {
            const data = { error: "You must be logged in to update your profile." };
            templateBuilder.append("error", data, "errors");
            return;
        }

        axios.put(url, profile, {
            headers: {
                Authorization: `Bearer ${user.token}`
            }
        })
        .then(() => {
            const data = {
                message: "The profile has been updated."
            };
            templateBuilder.append("message", data, "errors");
        })
        .catch(error => {
            const data = {
                error: "Save profile failed."
            };
            templateBuilder.append("error", data, "errors");
        });
    }
}

document.addEventListener("DOMContentLoaded", () => {
    profileService = new ProfileService();
});
