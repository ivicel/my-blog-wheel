"use strict";

import Vue from "vue";
import Message from "../components/SnackbarMessage.vue";

const colors = ["success", "info", "warning", "error"];

function Install(Vue, options) {
    function createMessage(opts = {}) {
        const cmp = new Vue(Message);
        Object.assign(cmp, options || {}, opts);
        document.body.appendChild(cmp.$mount().$el);
        cmp.open();

        return cmp.$el;
    }

    function show(opts = {}) {
        return createMessage(opts);
    }

    window.$message = show;
    Object.defineProperties(Vue.prototype, {
        $message: {
            get() {
                return show;
            }
        }
    });

    colors.forEach(color => {
        show[color] = (msg, opts) => show({ msg, color, ...opts });
    });
}

Vue.use(Install);

export default Install;
