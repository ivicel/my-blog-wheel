<template>
    <div>
        <v-layout wrap>
            <v-flex class="xs12 sm12 md3">
                <v-card
                    class="pa-2 ma-2 font-weight-medium white--text"
                    elevation="6"
                    color="#42A5F5"
                    hover
                    ripple
                    :to="{ name: 'postList' }"
                >
                    <v-card-title class="subheading">文章总数</v-card-title>
                    <v-card-text class="display-1">{{ postCount }}</v-card-text>
                </v-card>
            </v-flex>
            <v-flex class="xs12 sm12 md3">
                <v-card
                    class="pa-2 ma-2 font-weight-medium white--text"
                    elevation="6"
                    color="#00897B"
                    hover
                    ripple
                    :to="{ name: 'comment' }"
                >
                    <v-card-title class="subheading">评论总数</v-card-title>
                    <v-card-text class="display-1">{{
                        commentCount
                    }}</v-card-text>
                </v-card>
            </v-flex>
            <v-flex class="xs12 sm12 md3">
                <v-card
                    class="pa-2 ma-2 font-weight-medium white--text"
                    elevation="6"
                    color="#90A4AE"
                >
                    <v-card-title class="subheading">访问总数</v-card-title>
                    <v-card-text class="display-1">{{
                        visitCount
                    }}</v-card-text>
                </v-card>
            </v-flex>
            <v-flex class="xs12 sm12 md3">
                <v-card
                    class="pa-2 ma-2 font-weight-medium white--text"
                    elevation="6"
                    color="#E64A19"
                >
                    <v-card-title class="subheading">建立天数</v-card-title>
                    <v-card-text class="display-1">{{
                        createDays
                    }}</v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
        <v-layout wrap>
            <v-flex class="xs12 sm12 md6">
                <v-card class="ma-2 mt-4">
                    <v-card-title>最新动态</v-card-title>
                </v-card>
            </v-flex>
            <v-flex class="xs12 sm12 md6">
                <v-card class="ma-2 mt-4">
                    <v-card-title>操作记录</v-card-title>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
export default {
    data() {
        return {
            postCount: 0,
            commentCount: 0,
            visitCount: 0,
            createDays: 0
        };
    },
    created() {
        this.$axios.get("/post/summary").then(response => {
            this.postCount = response.data;
        });

        this.$axios.get("/comment/summary").then(reponsen => {
            this.commentCount = response.data;
        });
    }
};
</script>

<style>
</style>

