import Vue from "vue";
import Loading from "../components/Loading.vue";

function Install(Vue, options) {
    function create(opts = {}) {
        const loading = new Vue(Loading);
        Object.assign(loading, opts);
        document.body.appendChild(loading.$mount().$el);

        return loading;
    }

    const el = { loading: null };

    Object.defineProperties(Vue.prototype, {
        $loading: {
            get() {
                if (!el.loading) {
                    el.loading = create();
                }
                return el.loading;
            }
        }
    });
}

Vue.use(Install);

export default Install;
