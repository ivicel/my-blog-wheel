<template>
    <v-app>
        <v-container>
            <v-layout justify-center align-center>
                <v-snackbar
                    v-model="snackbar"
                    top
                    vertical
                    color="error"
                    :timeout="3000"
                >
                    {{ message }}
                    <v-btn dark flat @click="snackbar = false">关闭</v-btn>
                </v-snackbar>
                <v-flex xs12 sm6 md4>
                    <v-form lazy-validation ref="form">
                        <h3>登录</h3>
                        <v-text-field
                            clearable
                            v-model="user.username"
                            :rules="[rules.requireUsername]"
                            class="body-2 font-weight-regular"
                            label="请输入用户名"
                        ></v-text-field>
                        <v-text-field
                            clearable
                            v-model="user.password"
                            :rules="[rules.requirePassword]"
                            class="body-2 font-weight-regular"
                            label="请输入密码"
                            type="password"
                        ></v-text-field>
                        <v-checkbox
                            v-model="user.rememberMe"
                            label="Remember Me"
                        ></v-checkbox>
                        <v-btn class="primary submit" @click="login"
                            >登录</v-btn
                        >
                    </v-form>
                </v-flex>
            </v-layout>
        </v-container>
    </v-app>
</template>

<script>
import qs from "qs";

export default {
    data() {
        return {
            snackbar: false,
            message: "",
            user: {
                username: "",
                password: "",
                rememberMe: false
            },
            rules: {
                requireUsername: value => !!value || "用户不能为空",
                requirePassword: value => !!value || "密码不能为空"
            }
        };
    },
    methods: {
        login() {
            if (this.$refs.form.validate()) {
                this.$axios
                    .post("/login", qs.stringify(this.user))
                    .then(response => {
                        if (response.msg.status === 200) {
                            this.$router.push({
                                name: "home"
                            });
                        } else {
                            this.message = response.msg.msg;
                            this.snackbar = true;
                        }
                    });
            }
        }
    }
};
</script>

<style>
.submit {
    display: block;
    width: 100%;
    margin: 0;
}
</style>
