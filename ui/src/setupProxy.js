
const {createProxyMiddleware: proxy} = require('http-proxy-middleware');

module.exports = (app) => {
  app.use(
    proxy("/shop", {
      target: "http://localhost:9102", //跨域地址
      changeOrigin: true,
    }),
    proxy("/product", {
      target: "http://localhost:9101",
      changeOrigin: true,
    }),
    proxy("/order", {
      target: "http://localhost:9103",
      changeOrigin: true,
    })
  );
};
