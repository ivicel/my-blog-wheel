<template>
    <div>
        <confirm-dialog
            headline="删除确认"
            text="您确定要删除该分类么?"
            ref="dialog"
            @negative="cancelDelete()"
            @positive="confirmDelete()"
        ></confirm-dialog>
        <v-layout wrap>
            <v-flex xs12 sm12 md12 lg9>
                <v-card class="ma-2 pa-2">
                    <v-card-title class="subheading">
                        分类列表
                        <v-spacer />
                        <v-spacer />
                        <v-spacer />
                        <v-text-field
                            clearable
                            v-model="search"
                            append-icon="search"
                            placeholder="请输入分类名称"
                        ></v-text-field>
                    </v-card-title>
                    <v-data-table
                        :search="search"
                        :rows-per-page-items="cate.rowsPerPageItems"
                        rows-per-page-text="每页显示"
                        :headers="cate.headers"
                        :items="cate.items"
                    >
                        <template v-slot:items="props">
                            <tr>
                                <td>{{ props.item.name }}</td>
                                <td>{{ props.item.slugName }}</td>
                                <td>{{ props.item.description }}</td>
                                <td class="text-xs-center">
                                    {{ props.item.postCount }}
                                </td>
                                <td style="text-align: center">
                                    <v-btn
                                        flat
                                        class="d-inline-block ma-0 pa-2"
                                        color="primary"
                                        @click="edit(props.item)"
                                        >编辑</v-btn
                                    >
                                    <v-btn
                                        flat
                                        class="d-inline-block ma-0 pa-2"
                                        color="red"
                                        @click="deleteCate(props.item)"
                                        >删除</v-btn
                                    >
                                </td>
                            </tr>
                        </template>
                    </v-data-table>
                </v-card>
            </v-flex>
            <v-flex xs12 sm12 md12 lg3>
                <v-card class="ma-2">
                    <v-card-title class="subheading"
                        >添加/修改分类</v-card-title
                    >
                    <v-form class="pr-4 pl-4" ref="editForm">
                        <v-text-field
                            v-model="editItem.name"
                            :rules="[rules.requireName]"
                            label="请输入分类名称"
                        ></v-text-field>
                        <v-text-field
                            v-model="editItem.slugName"
                            :rules="[rules.requireSlugName]"
                            label="请输入分类别名"
                        ></v-text-field>
                        <v-select
                            v-model="editItem.parentId"
                            label="请选择上级分类"
                            :items="cate.items"
                            item-text="name"
                            item-value="id"
                        ></v-select>
                        <v-textarea
                            label="请输入分类描述"
                            v-model="editItem.description"
                        ></v-textarea>
                    </v-form>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                            color="green dark lighten-2"
                            flat
                            v-show="editItem.id > 0"
                            @click="backToAdd"
                            >返回新增</v-btn
                        >
                        <v-btn color="primary" flat @click="save">保存</v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
import ConfirmDialog from "@/components/ConfirmDialog.vue";

export default {
    components: {
        ConfirmDialog
    },
    data() {
        return {
            cate: {
                dataLoading: false,
                headers: [
                    {
                        text: "名称",
                        value: "name"
                    },
                    {
                        text: "别名",
                        value: "slugNmae"
                    },
                    {
                        text: "描述",
                        value: "description"
                    },
                    {
                        text: "文章数",
                        value: "postCount",
                        align: "center"
                    },
                    {
                        text: "操作",
                        value: "action",
                        align: "center",
                        sortable: false
                    }
                ],
                rowsPerPageItems: [10, 20, 30],
                items: []
            },
            editItem: {
                id: 0,
                name: "",
                slugName: "",
                description: "",
                parentId: 0
            },
            tmpItem: null,
            deleteItem: null,
            search: "",
            rules: {
                requireName: value => !!value || "分类名称不能为空",
                requireSlugName: value => !!value || "分类别名不能为空"
            },
            dialog: {
                loading: false,
                cancel() {},
                confirm() {}
            }
        };
    },
    created() {
        this.fetchAllCategory();
    },
    methods: {
        deleteCate(item) {
            if (item.postCount > 0) {
                this.$message.warning(`分类下文章数量不为 0, 不能删除`);
                return;
            }

            this.deleteItem = item;
            this.$refs.dialog.show();
        },
        cancelDelete() {
            this.$refs.dialog.dismiss();
        },
        confirmDelete() {
            this.$refs.dialog.dismiss();
            this.$loading.show("删除中...");
            this.$axios
                .delete(`/category/${this.deleteItem.id}`)
                .then(response => {
                    this.cate.items.splice(
                        this.cate.items.indexOf(this.deleteItem),
                        1
                    );
                    this.$message.success(response.data.msg);
                })
                .catch(error => {
                    this.$message.error(error.response.data.msg);
                })
                .finally(() => {
                    this.$loading.dismiss();
                });
        },
        edit(item) {
            this.tmpItem = item;
            Object.assign(this.editItem, item);
            if (!item.parentId) {
                this.editItem.parentId = item.id;
            }
        },
        backToAdd() {
            this.editItem = {
                id: 0,
                name: "",
                slugName: "",
                description: "",
                parentId: 0
            };
            this.$refs.editForm.reset();
        },
        save() {
            if (!this.$refs.editForm.validate()) {
                return;
            }

            this.$loading.show();
            if (this.editItem.id) {
                // update
                this.$axios
                    .put(`/category/${this.editItem.id}`, this.editItem)
                    .then(response => {
                        if (response.status === 200) {
                            this.$message.success(response.data.msg);

                            Object.assign(this.tmpItem, this.editItem);
                            this.$refs.editForm.reset();
                        }
                    })
                    .catch(error => {
                        if (error.response) {
                            this.$message.error(error.response.data.msg);
                        }
                    })
                    .finally(() => {
                        this.$loading.dismiss();
                    });
            } else {
                // create new
                this.$axios
                    .post(`/category`, this.editItem)
                    .then(response => {
                        if (response.status === 200) {
                            this.cate.items.push(
                                Object.assign({}, response.data)
                            );
                            this.$message.success("成功添加新分类");
                            this.$refs.editForm.reset();
                        }
                    })
                    .catch(error => {
                        if (error.response) {
                            this.$message.error(error.response.data.msg);
                        }
                    })
                    .finally(() => {
                        this.$loading.dismiss();
                    });
            }
        },
        fetchAllCategory() {
            this.cate.dataLoading = true;
            this.$axios
                .get("/category/all")
                .then(response => {
                    this.cate.items = response.data;
                })
                .finally(() => {
                    this.cate.dataLoading = false;
                });
        }
    }
};
</script>

<style scoped>
.v-btn {
    min-width: 36px;
}
</style>
