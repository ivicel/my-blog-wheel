module.exports = {
    root: true,
    env: {
        node: true
    },
    extends: [
        "plugin:vue/essential",
        "plugin:prettier/recommended",
        "@vue/prettier"
    ],
    rules: {
        "no-console": process.env.NODE_ENV === "production" ? "error" : "off",
        "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
        "no-unused-vars": "off",
        indent: ["error", 4],
        "no-unused-components": "off",
        "prettier/prettier": ["error"]
    },
    parserOptions: {
        parser: "babel-eslint"
    }
};
