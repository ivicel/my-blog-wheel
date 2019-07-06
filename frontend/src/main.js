import "material-design-icons-iconfont/dist/material-design-icons.css";
import Vue from "vue";
import VueClipboard from "vue-clipboard2";
import App from "./App.vue";
import "./plugins/axios";
import "./plugins/loading";
import "./plugins/message";
import "./plugins/vuetify";
import router from "./router";

Vue.use(VueClipboard);

new Vue({
    router,
    render: h => h(App),
    data() {
        return {
            app: new Vue()
        };
    }
}).$mount("#app");
