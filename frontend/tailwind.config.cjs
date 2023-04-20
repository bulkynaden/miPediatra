module.exports = {
    content: ["@/index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
    darkMode: "class", // or 'media' or 'class'
    theme: {
        extend: {
            colors: {
                transparent: "transparent",
                current: "currentColor",
                primary: "#a855f7",
                success: "#10b981",
                danger: "#ef4444",
                warning: "#f59e0b",
                info: "#3b82f6",
                light: "#6b7280",
                dark: "#202020",
                foreground: "#292929",
            },
            textColor: {
                test: "#ef4444",
            },
            transitionDuration: {
                240: "240ms",
            },
        },
    },

    plugins: [require("@tailwindcss/forms")],
};
