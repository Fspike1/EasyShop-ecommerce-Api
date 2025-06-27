console.log("✅ mood-quiz.js is loaded!");

document.addEventListener("DOMContentLoaded", () => {
    const moodButtons = document.querySelectorAll(".mood-btn");
    const kitResult = document.getElementById("kit-result");
    const kitMessage = document.getElementById("kit-message");
    const addToCartBtn = document.getElementById("add-to-cart-btn");
    const skipBtn = document.getElementById("skip-btn");

    console.log("📍 Mood buttons found:", moodButtons.length);

    const moodKitMap = {
        overwhelmed: { name: "Reset Box", id: 92 },
        hopeful: { name: "Calm Kit", id: 91 },
        heavy: { name: "Reset Box", id: 92 },
        creative: { name: "Reset Box", id: 92 },
        joyful: { name: "Calm Kit", id: 91 },
        disconnected: { name: "Calm Kit", id: 91 }
    };

    let selectedKit = null;

    moodButtons.forEach(button => {
        button.addEventListener("click", () => {
            const mood = button.dataset.mood;
            const kit = moodKitMap[mood];
            console.log("🧠 Mood clicked:", mood, kit);

            // Always show the result container and skip button
            kitResult.style.display = "block";
            skipBtn.style.display = "inline-block";

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
        if (!selectedKit) return;

        const url = `${config.baseUrl}/cart/products/${selectedKit.id}`;
        console.log("🛒 Adding to cart:", selectedKit);

        axios.post(url, {}, {
            headers: userService.getHeaders()
        })
        .then(() => {
            alert(`${selectedKit.name} was added to your cart!`);

            // ✅ Reload the cart so the UI updates
            cartService.loadCart();

            // ✅ Hide the quiz section after success
            const quizSection = document.getElementById("mood-quiz");
            if (quizSection) quizSection.style.display = "none";

            // ✅ Optionally, scroll to top or main content
            window.scrollTo({ top: 0, behavior: 'smooth' });
        })
        .catch(err => {
            console.error("❌ Error adding to cart:", err);
            alert("There was a problem adding your kit to the cart.");
        });
    });


   skipBtn.addEventListener("click", () => {
     window.location.href = "index.html";
   });

});
