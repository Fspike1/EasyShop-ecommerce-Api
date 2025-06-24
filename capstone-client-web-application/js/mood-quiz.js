document.addEventListener("DOMContentLoaded", () => {
    const moodButtons = document.querySelectorAll(".mood-btn");
    const kitResult = document.getElementById("kit-result");
    const kitMessage = document.getElementById("kit-message");
    const addToCartBtn = document.getElementById("add-to-cart-btn");
    const skipBtn = document.getElementById("skip-btn");

    const moodKitMap = {
        overwhelmed: { name: "Reset Box", id: 92 },
        heavy: { name: "Reset Box", id: 92 },
        hopeful: { name: "Calm Kit", id: 91 },
    };

    let selectedKit = null;

    moodButtons.forEach(button => {
        button.addEventListener("click", () => {
            const mood = button.getAttribute("data-mood");
            const kit = moodKitMap[mood];

            kitResult.style.display = "block";

            if (kit) {
                kitMessage.textContent = `We recommend the ${kit.name} for how you're feeling. Would you like to add it to your cart?`;
                addToCartBtn.style.display = "inline-block";
                selectedKit = kit;
            } else {
                kitMessage.textContent = "We're creating a kit just for you – new mood kits coming soon!";
                addToCartBtn.style.display = "none";
                selectedKit = null;
            }
        });
    });

    addToCartBtn.addEventListener("click", () => {
        if (selectedKit) {
            const url = `${config.baseUrl}/cart/products/${selectedKit.id}`;
            axios.post(url)
                .then(response => {
                    console.log("✅ Kit added to cart:", response.data);
                    window.location.href = "index.html";
                })
                .catch(error => {
                    console.error("❌ Error adding to cart:", error.response || error);
                    alert("There was a problem adding your kit to the cart. Are you logged in?");
                });
        }
    });

    skipBtn.addEventListener("click", () => {
        window.location.href = "index.html";
    });
});
