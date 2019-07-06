<template>
    <v-navigation-drawer app width="230" v-model="show">
        <v-toolbar>
            <v-toolbar-title>MyBlog</v-toolbar-title>
        </v-toolbar>

        <v-list>
            <v-list-tile :to="{ name: 'dashboard' }" ripple exact>
                <v-list-tile-action>
                    <v-icon>dashboard</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                    Dashboard
                </v-list-tile-content>
            </v-list-tile>
            <v-list-tile :to="{ name: 'post' }" ripple :exact="exact">
                <v-list-tile-action>
                    <v-icon>edit</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                    发表文章
                </v-list-tile-content>
            </v-list-tile>
            <v-list-tile :to="{ name: 'postList' }" ripple exact>
                <v-list-tile-action>
                    <v-icon>textsms</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                    文章管理
                </v-list-tile-content>
            </v-list-tile>
            <v-list-tile :to="{ name: 'category' }" ripple>
                <v-list-tile-action>
                    <v-icon>archive</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                    分类管理
                </v-list-tile-content>
            </v-list-tile>
            <v-list-tile :to="{ name: 'tag' }" ripple>
                <v-list-tile-action>
                    <v-icon>label</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                    标签管理
                </v-list-tile-content>
            </v-list-tile>
            <v-list-tile :to="{ name: 'comment' }" ripple>
                <v-list-tile-action>
                    <v-icon>comment</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                    评论管理
                </v-list-tile-content>
            </v-list-tile>
            <v-list-tile :to="{ name: 'attachment' }" ripple>
                <v-list-tile-action>
                    <v-icon>attachment</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                    附件管理
                </v-list-tile-content>
            </v-list-tile>
            <v-list-tile :to="{ name: 'personal' }" ripple>
                <v-list-tile-action>
                    <v-icon>person</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                    个人信息
                </v-list-tile-content>
            </v-list-tile>
        </v-list>
    </v-navigation-drawer>
</template>

<script>
export default {
    data() {
        return {
            show: this.$vuetify.breakpoint.lgAndUp,
            exact: true
        };
    },
    created() {
        this.$root.app.$on("app-drawer-toggle", () => {
            this.show = !this.show;
        });
    },
    watch: {
        $route: function(to, from) {
            this.exact = !/^\/post\/\d+/.test(to.path);
        }
    }
};
</script>

<style scoped>
.active {
    background: rgba(0, 0, 0, 0.04);
}
</style>
