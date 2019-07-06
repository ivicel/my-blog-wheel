<template>
    <v-layout>
        <confirm-dialog
            headline="删除确认"
            text="您确定要删除这篇文章么?"
            ref="dialog"
            @negative="dialogNegative()"
            @positive="dialogPositive()"
        ></confirm-dialog>
        <v-dialog v-model="postPasswordDialog" width="300">
            <v-card>
                <v-card-text>
                    <v-form ref="postPasswordField">
                        <v-text-field
                            clearable
                            label="请输入文章密码"
                            v-model="postPassword"
                            :rules="[rules.require]"
                        ></v-text-field>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer />
                    <v-btn
                        flat
                        color="primary"
                        @click="clearPostPassword"
                        :class="{
                            'v-btn--disabled':
                                selectedItem && !selectedItem.passwordHash
                        }"
                        >清除密码</v-btn
                    >
                    <v-btn flat color="red" @click="changePostPassword"
                        >设置密码</v-btn
                    ></v-card-actions
                >
            </v-card>
        </v-dialog>
        <v-dialog v-model="progressDialog" persistent width="300">
            <v-card color="primary" dark>
                <v-card-text
                    >操作中 ...
                    <v-progress-linear
                        indeterminate
                        color="white"
                        class="mb-0"
                        height="4"
                    ></v-progress-linear>
                </v-card-text>
            </v-card>
        </v-dialog>
        <v-flex>
            <v-card class="pt-2">
                <v-form ref="searchForm" class="wrap">
                    <v-layout ml-4 mr-4 mt-2 mb-2 wrap row>
                        <v-flex xs12 sm12 md3 lg3
                            ><v-text-field
                                label="关键词"
                                v-model="search.keyword"
                            ></v-text-field
                        ></v-flex>
                        <v-flex xs12 sm12 md2 lg2
                            ><v-select
                                label="状态"
                                :items="postStatus"
                                v-model="search.status"
                            ></v-select
                        ></v-flex>
                        <v-flex xs12 sm12 md2 lg2
                            ><v-select
                                label="分类"
                                v-model="search.categoryId"
                                :items="categories"
                                item-text="name"
                                item-value="id"
                            ></v-select
                        ></v-flex>
                        <v-flex xs12 sm12 md5 lg5 row justify-start nowrap grow>
                            <v-btn class="success" @click="fetchPost"
                                >查询</v-btn
                            >
                            <v-btn
                                class="blue-grey lighten-2"
                                color="white--text"
                                @click="resetSearchForm"
                                >重置</v-btn
                            >
                            <v-flex v-if="selectItems.length > 0" d-inline-flex>
                                <v-menu offset-overflow>
                                    <template v-slot:activator="{ on }">
                                        <v-btn class="primary" v-on="on">
                                            移动到
                                        </v-btn>
                                    </template>
                                    <v-list>
                                        <v-list-tile
                                            v-for="item in postStatus"
                                            :key="item.value"
                                            @click="
                                                changePostStatus(
                                                    selectItems,
                                                    item.value
                                                )
                                            "
                                        >
                                            <v-list-tile-content>
                                                <v-list-tile-title
                                                    v-text="item.text"
                                                >
                                                </v-list-tile-title>
                                            </v-list-tile-content>
                                        </v-list-tile>
                                    </v-list>
                                </v-menu>
                                <v-btn
                                    class="error"
                                    @click="deletePost(selectItems)"
                                    >删除</v-btn
                                >
                            </v-flex>
                        </v-flex>
                    </v-layout>
                </v-form>
                <v-data-table
                    class="elevation-1 pa-2"
                    hide-actions
                    v-model="selectItems"
                    :headers="headers"
                    :items="items"
                    :rows-per-page-items="rowsPerPageItems"
                    :total-items="pagination.totalItems"
                    :pagination.sync="pagination"
                    :loading="loading"
                    select-all
                    @update:pagination="update"
                >
                    <v-icon>dashboard</v-icon>
                    <template v-slot:items="props">
                        <td>
                            <v-checkbox
                                primary
                                hide-details
                                v-model="props.selected"
                            ></v-checkbox>
                        </td>
                        <td>{{ props.item.title }}</td>
                        <td>
                            <a
                                class="post-password"
                                @click="showPostPasswordDialog(props.item)"
                                >{{ props.item.passwordHash ? "有" : "无" }}
                                <v-icon small>edit</v-icon></a
                            >
                        </td>
                        <td>
                            {{
                                postStatus.find(
                                    p => p.value === props.item.status
                                ).text
                            }}
                        </td>
                        <td>{{ props.item.visitCount }}</td>
                        <td>{{ props.item.commentCount }}</td>
                        <td>{{ props.item.lastModifiedDate }}</td>
                        <td class="text-xs-center">
                            <v-btn
                                class="ma-0 pa-2"
                                flat
                                color="primary"
                                @click="editPost(props.item.id)"
                                >编辑</v-btn
                            >
                            <v-btn
                                flat
                                color="red"
                                @click="deletePost([props.item])"
                                class="ma-0 pa-2"
                                >删除</v-btn
                            >
                        </td>
                    </template>
                </v-data-table>
                <v-layout pa-3 v-show="pagination.page">
                    <v-pagination
                        v-model="pagination.page"
                        :length="pagination.totalPages"
                        :total-visible="10"
                        @input="fetchPost"
                    ></v-pagination>
                </v-layout>
            </v-card>
        </v-flex>
        <v-btn
            class="ligthen-2 red white--text"
            fixed
            fab
            bottom
            right
            :to="{ name: 'post' }"
            ><v-icon>edit</v-icon></v-btn
        >
    </v-layout>
</template>

<script>
import ConfirmDialog from "@/components/ConfirmDialog.vue";
import qs from "qs";

export default {
    data() {
        return {
            headers: [
                {
                    text: "标题",
                    value: "title",
                    width: 270
                },
                {
                    text: "密码",
                    value: "passwordHash"
                },
                {
                    text: "状态",
                    value: "status",
                    width: 130
                },
                {
                    text: "访问量",
                    value: "visitCount"
                },
                {
                    text: "评论数",
                    value: "commentCount"
                },
                {
                    text: "更新日期",
                    value: "lastModifiedDate"
                },
                {
                    text: "操作",
                    value: "action",
                    align: "center",
                    sortable: false
                }
            ],
            selectItems: [],
            items: [],
            rowsPerPageItems: [10, 20, 30, 40],
            loading: true,
            pagination: {
                page: 1,
                rowsPerPage: 10,
                totalPages: 0,
                totalItems: 0,
                sortBy: "lastModifiedDate",
                descending: true
            },
            prevSort: {
                sortBy: "lastModifiedDate",
                descending: true
            },
            postStatus: [
                { value: "PUBLISHED", text: "已发布" },
                { value: "DRAFT", text: "草稿箱" },
                { value: "RECYCLE", text: "回收站" }
            ],
            search: {
                keyword: "",
                status: "",
                categoryId: ""
            },
            selectedItem: null,
            progressDialog: false,
            postPasswordDialog: false,
            postPassword: "",
            rules: {
                require: value => !!value || "密码不能为空"
            },
            categories: []
        };
    },
    components: {
        ConfirmDialog
    },
    created() {
        this.fetchPost();
        this.$axios.get("/category/all").then(response => {
            this.categories = response.data;
        });
    },
    methods: {
        deletePost(posts) {
            this.selectItems = posts;
            this.$refs.dialog.show();
        },
        dialogNegative() {
            this.$refs.dialog.dismiss();
        },
        dialogPositive() {
            let success = true;
            this.selectItems.forEach(item => {
                this.$axios
                    .delete(`/post/${item.id}`)
                    .then(response => {
                        // 删除成功
                        this.items.splice(this.items.indexOf(item), 1);
                    })
                    .catch(error => {
                        success = false;
                        if (error.response) {
                            this.$message.warning(
                                error.response.msg ||
                                    `无法删除文章: ${item.title}`
                            );
                        } else if (error.request) {
                            this.$message.info(`无法删除文章: ${item.title}`);
                        }
                    })
                    .finally(() => {
                        this.$refs.dialog.dismiss();
                    });
            });

            if (success) {
                this.$message.success("删除成功");
            }
        },
        update() {
            if (this.pagination.sortBy === null) {
                this.pagination.sortBy = this.prevSort.sortBy;
                this.pagination.descending = !this.prevSort.descending;
            } else {
                this.prevSort.sortBy = this.pagination.sortBy;
                this.prevSort.descending = this.pagination.descending;
            }
            this.fetchPost();
        },
        fetchPost() {
            this.loading = true;
            this.$axios
                .get(
                    "/post?" +
                        qs.stringify({
                            keyword: this.search.keyword,
                            status: this.search.status,
                            categoryId: this.search.categoryId,
                            page: this.pagination.page - 1,
                            size: this.pagination.rowsPerPage,
                            sort:
                                `${this.pagination.sortBy},` +
                                (this.pagination.descending ? "desc" : "asc")
                        })
                )
                .then(response => {
                    this.items = response.data.content;
                    this.pagination.page = response.data.number + 1;
                    this.pagination.rowsPerPage = response.data.size;
                    this.pagination.totalItems = response.data.totalElements;
                    this.pagination.totalPages = response.data.totalPages;
                })
                .finally(() => {
                    this.loading = false;
                });
        },
        changePostStatus(posts, status) {
            this.progressDialog = true;
            this.$axios
                .put(`/post/status`, {
                    ids: posts.map(p => p.id),
                    status: status
                })
                .then(response => {
                    this.progressDialog = false;
                    posts.forEach(p => (p.status = status));
                })
                .catch(error => {});
        },
        editPost(id) {
            this.$router.push(`/post/${id}`);
        },
        showPostPasswordDialog(item) {
            this.selectedItem = item;
            this.postPasswordDialog = true;
        },
        clearPostPassword() {
            if (!this.selectedItem.passwordHash) {
                return;
            }

            this.postPasswordDialog = false;
            this.progressDialog = true;
            this.$axios
                .delete(`/post/${this.selectedItem.id}/password`)
                .then(response => {
                    if (response.status === 200) {
                        this.$message.info("密码删除成功");
                        this.selectedItem.passwordHash = "";
                    }
                })
                .finally(() => {
                    this.progressDialog = false;
                });
        },
        changePostPassword() {
            if (!this.$refs.postPasswordField.validate()) {
                return;
            }

            this.postPasswordDialog = false;
            this.progressDialog = true;
            this.$axios
                .put(
                    `/post/${this.selectedItem.id}/password`,
                    qs.stringify({ password: this.postPassword }),
                    {
                        "Content-Type": "application/x-www-form-urlencode"
                    }
                )
                .then(response => {
                    if (response.status === 200) {
                        this.$message.info("设置密码成功");
                        this.selectedItem.passwordHash = "success";
                    }
                })
                .catch(error => {
                    if (error.request) {
                        this.$message.error(error.request.response.msg);
                    } else {
                        this.$message.error(error);
                    }
                })
                .finally(() => {
                    this.progressDialog = false;
                    this.$refs.postPasswordField.reset();
                });
        },
        resetSearchForm() {
            this.$refs.searchForm.reset();
            this.pagination = {
                page: 1,
                rowsPerPage: 10,
                totalPages: 0,
                totalItems: 0,
                sortBy: "lastModifiedDate",
                descending: true
            };
            this.fetchPost();
        }
    },
    watch: {
        postPasswordDialog() {
            this.$refs.postPasswordField.reset();
        }
    }
};
</script>

<style lang="css" scoped>
.v-btn {
    min-width: 36px;
}

.post-password {
    text-decoration: none;
    color: #000;
}
</style>
