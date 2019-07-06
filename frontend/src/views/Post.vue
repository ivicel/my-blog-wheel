<template>
    <div>
        <v-form ref="form">
            <v-layout>
                <v-flex xs12 sm12 md8>
                    <v-text-field
                        class="body-2 font-weight-regular"
                        clearable
                        label="请输入文章标题"
                        v-model="post.title"
                        :rules="[rules.required]"
                    >
                        <template v-slot:prepend>
                            <v-chip class="ma-0 pa-0">标题:</v-chip>
                        </template>
                    </v-text-field>
                </v-flex>
            </v-layout>

            <v-layout>
                <v-flex xs12 sm12 md8>
                    <v-combobox
                        class="body-2 font-weight-regular"
                        item-text="name"
                        item-value="id"
                        clearable
                        hide-selected
                        multiple
                        label="请输入或选择已有分类"
                        :items="categories"
                        v-model="post.categories"
                    >
                        <template v-slot:prepend>
                            <v-chip class="ma-0 pa-0">分类:</v-chip>
                        </template>

                        <template
                            v-slot:selection="{ item, index, parent, selected }"
                        >
                            <v-chip
                                close
                                :color="colors[index % colors.length]"
                                @input="removeCategory(parent, item)"
                                >{{ item }}</v-chip
                            >
                        </template>
                    </v-combobox>
                </v-flex>
            </v-layout>

            <v-layout>
                <v-flex xs12 sm12 md8>
                    <v-combobox
                        item-text="name"
                        item-value="id"
                        clearable
                        hide-selected
                        multiple
                        label="请输入或选择已有标签"
                        :items="tags"
                        v-model="post.tags"
                    >
                        <template v-slot:prepend>
                            <v-chip class="ma-0 pa-0">标签:</v-chip>
                        </template>

                        <template
                            v-slot:selection="{ item, index, parent, selected }"
                        >
                            <v-chip
                                close
                                :color="colors[index % colors.length]"
                                @input="removeTag(parent, item)"
                                >{{ item }}</v-chip
                            >
                        </template>
                    </v-combobox>
                </v-flex>
            </v-layout>

            <v-layout class="mb-4">
                <v-flex xs12 sm12 md8>
                    <v-text-field
                        class="body-2 font-weight-regular"
                        clearable
                        label="为文章设置一个访问密码"
                        v-model="post.password"
                    >
                        <template v-slot:prepend>
                            <v-chip class="ma-0 pa-0">文章密码:</v-chip>
                        </template>
                    </v-text-field>
                </v-flex>
            </v-layout>

            <markdown-editor v-model="post.body" ref="editor"></markdown-editor>
        </v-form>
        <v-layout justify-end>
            <v-btn
                color="warning"
                :disabled="btnDisabled"
                :loading="draftLoading"
                @click="save('DRAFT')"
                >存草稿</v-btn
            >
            <v-btn
                color="info"
                :disabled="btnDisabled"
                :loading="publishedLoading"
                @click="save('PUBLISHED')"
                >发表</v-btn
            >
        </v-layout>
    </div>
</template>

<script>
import MarkdownEditor from "../components/MarkdownEditor";

export default {
    data() {
        return {
            searchText: "",
            checked: "",
            rules: {
                required: value => !!value || "标题不能为空"
            },
            btnDisabled: false,
            draftLoading: false,
            publishedLoading: false,
            categories: [],
            tags: [],
            post: {
                title: "",
                categories: [],
                author: {},
                tags: [],
                password: "",
                body: "",
                bodyHtml: "",
                status: ""
            },
            colors: [
                "red",
                "green",
                "purple",
                "deep-grey",
                "indigo",
                "light-green",
                "deep-purple",
                "lime",
                "teal",
                "cyan",
                "light-blue",
                "amber",
                "pink",
                "orange",
                "grey",
                "brown",
                "blue"
            ]
        };
    },
    components: {
        MarkdownEditor
    },
    methods: {
        removeCategory(parent, item) {
            parent.selectItem(item);
        },
        removeTag(parent, item) {
            parent.selectItem(item);
        },
        save(status) {
            if (!this.$refs.form.validate()) {
                return;
            }

            this.post.status = status;
            this.post.bodyHtml = this.$refs.editor.renderHtml();
            this.btnDisabled = true;
            this.draftLoading = status == "DRAFT";
            this.publishedLoading = !this.draftLoading;

            this.$axios
                .post("/post", this.post)
                .then(response => {
                    this.$message.info(response.data.msg);
                    setTimeout(() => {
                        this.$router.push({ name: "postList" });
                    }, 3000);
                })
                .catch(error => {
                    if (error.response) {
                        this.$message.error(
                            error.response.data.msg || "操作失败"
                        );
                    } else {
                        this.$message.error("操作失败");
                    }
                })
                .finally(() => {
                    this.btnDisabled = false;
                    this.publishedLoading = this.draftLoading = false;
                });
        }
    },
    created() {
        this.$axios.get("/category/all").then(response => {
            this.categories = response.data.map(v => v.name);
        });

        this.$axios.get("/tag/all").then(response => {
            this.tags = response.data.map(v => v.name);
        });

        // todo: 404
        if (this.$route.params.id) {
            this.$axios
                .get(`/post/${this.$route.params.id}`)
                .then(response => {
                    this.post = response.data;
                    this.post.categories = response.data.categories.map(
                        v => v.name
                    );
                    this.post.tags = response.data.tags.map(v => v.name);
                })
                .catch(error => {
                    if (error.response) {
                        if (error.response.status === 404) {
                            this.$router.push({ name: "notFound" });
                        } else {
                            this.$message.error(error.response.data);
                        }
                    }
                });
        }
    },
    watch: {
        $route: function(to, from) {
            if (to.path === "/post") {
                this.post = {
                    title: "",
                    categories: [],
                    author: {},
                    tags: [],
                    password: "",
                    body: "",
                    bodyHtml: "",
                    status: ""
                };
                this.$refs.form.resetValidation();
            }
        }
    }
};
</script>

<style scoped>
</style>
