/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            colors: {
                border: "#DADADA"
            },
            boxShadow: {
                card: "0 2px 4px 0 rgba(38, 59, 56, 0.10), 0 0 0 1.5px rgba(38, 50, 56, 0.10)",
            }
        },
    },
    "plugins": ["prettier-plugin-tailwindcss"]
}