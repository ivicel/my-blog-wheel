module.exports = {
    devServer: {
        proxy: {
            "/api": {
                target: "http://localhost:8088/blog",
                ws: true,
                changeOrigin: true
            },
            "/blog/uploads": {
                target: "http://localhost:8088"
            }
        },
        port: 8080
    },
    publicPath: process.env.NODE_ENV === "production" ? "/blog/" : "/",
    outputDir: "../src/main/resources/public/admin",
    assetsDir: "admin/static",
    productionSourceMap: false
};
