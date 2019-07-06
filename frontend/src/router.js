import Vue from "vue";
import Router from "vue-router";
import Attachment from "./views/Attachment.vue";
import Category from "./views/Category.vue";
import Comment from "./views/Comment.vue";
import DashBoard from "./views/DashBoard.vue";
import Home from "./views/Home.vue";
import Login from "./views/Login.vue";
import NotFound from "./views/NotFound.vue";
import Personal from "./views/Personal.vue";
import Post from "./views/Post.vue";
import PostList from "./views/PostList.vue";
import Tag from "./views/Tag.vue";

Vue.use(Router);

const router = new Router({
    mode: "history",
    base: `${process.env.BASE_URL}admin`,
    routes: [
        {
            path: "/",
            component: Home,
            children: [
                {
                    path: "",
                    name: "dashboard",
                    component: DashBoard
                },
                {
                    path: "post/all",
                    name: "postList",
                    component: PostList
                },
                {
                    path: "post/:id?",
                    name: "post",
                    component: Post
                },
                {
                    path: "category",
                    name: "category",
                    component: Category
                },
                {
                    path: "comment",
                    name: "comment",
                    component: Comment
                },
                {
                    path: "tag",
                    name: "tag",
                    component: Tag
                },
                {
                    path: "attachment",
                    name: "attachment",
                    component: Attachment
                },
                {
                    path: "personal",
                    name: "personal",
                    component: Personal
                }
            ]
        },
        {
            path: "/login",
            name: "login",
            component: Login
        },
        {
            path: "*",
            name: "notFound",
            component: NotFound
        }
    ]
});

export default router;
