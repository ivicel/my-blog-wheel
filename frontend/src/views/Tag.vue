<template>
    <div>
        <v-layout wrap>
            <confirm-dialog
                ref="dialog"
                headline="删除确认"
                text="您确认要删除该标签么?"
                @negative="cancelDelete()"
                @positive="confirmDelete()"
            ></confirm-dialog>
            <v-flex xs12 sm12 md12 lg9>
                <v-card class="ma-2">
                    <v-card-title>
                        <h3>标签列表</h3>
                        <v-spacer></v-spacer>
                        <v-spacer></v-spacer>
                        <v-spacer></v-spacer>
                        <v-text-field
                            v-model="tags.search"
                            append-icon="search"
                            label="search"
                            single-line
                            hide-details
                        ></v-text-field>
                    </v-card-title>
                    <v-data-table
                        class="elevation-1 pa-2"
                        :headers="tags.headers"
                        :items="tags.items"
                        :search="tags.search"
                        :rows-per-page-items="tags.rowsPerPageItems"
                        :loading="tags.dataLoading"
                    >
                        <v-icon>dashboard</v-icon>
                        <template v-slot:items="props">
                            <td>{{ props.index + 1 }}</td>
                            <td>{{ props.item.name }}</td>
                            <td class="text-xs-center">
                                {{ props.item.associatedPostCount }}
                            </td>
                            <td class="text-md-center">
                                <v-btn
                                    flat
                                    class="ma-0 pa-2"
                                    color="primary"
                                    @click="editTag(props.item)"
                                    >编辑</v-btn
                                >
                                <v-btn
                                    flat
                                    color="red"
                                    class="ma-0 pa-2"
                                    @click="deleteTag(props.item)"
                                    >删除</v-btn
                                >
                            </td>
                        </template>
                    </v-data-table>
                </v-card>
            </v-flex>
            <v-flex xs12 sm12 md12 lg3>
                <v-card class="ma-2">
                    <v-card-title class="subheading"
                        >添加/修改标签</v-card-title
                    >
                    <v-form class="pr-4 pl-4" ref="editForm">
                        <v-text-field
                            label="请输入标签名称"
                            v-model="editItem.name"
                            :rules="[rules.required]"
                        ></v-text-field>
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
        "confirm-dialog": ConfirmDialog
    },
    data() {
        return {
            tags: {
                loading: true,
                headers: [
                    {
                        text: "#",
                        value: "index",
                        sortable: false
                    },
                    {
                        text: "名称",
                        value: "name"
                    },
                    {
                        text: "文章数量",
                        value: "associatedPostCount",
                        align: "center"
                    },
                    {
                        text: "操作",
                        value: "action",
                        align: "center",
                        sortable: false
                    }
                ],
                items: [],
                rowsPerPageItems: [10, 20, 30, 40]
            },
            rules: {
                required: value => !!value || "名称不能为空"
            },
            dialog: {
                loading: false,
                cancel() {},
                confirm() {}
            },
            editItem: {
                id: 0,
                name: "",
                associatedPostCount: 0
            },
            tmpItem: null
        };
    },
    created() {
        this.fetch();
    },
    methods: {
        fetch() {
            this.$axios
                .get("/tag/all")
                .then(response => {
                    this.tags.items = response.data;
                })
                .finally(() => {
                    this.tags.dataLoading = false;
                });
        },
        deleteTag(item) {
            if (item.associatedPostCount) {
                this.$message.error("标签下文章数量不为 0, 不能删除");
                return;
            }

            this.tmpItem = item;
            this.$refs.dialog.show();
        },
        cancelDelete() {
            this.$refs.dialog.dismiss();
        },
        confirmDelete() {
            this.$axios
                .delete(`/tag/${this.tmpItem.id}`)
                .then(response => {
                    if (response.status === 200) {
                        this.tags.items.splice(
                            this.tags.items.indexOf(this.tmpItem),
                            1
                        );
                        this.$message.success("删除成功");
                    }
                })
                .finally(() => {
                    this.$refs.dialog.dismiss();
                });
        },
        editTag(item) {
            this.tmpItem = item;
            Object.assign(this.editItem, item);
        },
        backToAdd() {
            Object.assign(this.editItem, {
                id: 0,
                name: "",
                slugName: ""
            });
            this.$refs.editForm.reset();
        },
        save() {
            if (!this.$refs.editForm.validate()) {
                return;
            }

            if (this.editItem.id) {
                // update
                this.$axios
                    .put(`/tag/${this.editItem.id}`, this.editItem)
                    .then(response => {
                        if (response.status === 200) {
                            Object.assign(this.tmpItem, this.editItem);
                            this.$message.success("更新成功");
                        }
                    })
                    .catch(error => {
                        if (error.response) {
                            this.$message.error(error.response.data.msg);
                        }
                    });
            } else {
                // create
                this.$axios
                    .post("/tag", this.editItem)
                    .then(response => {
                        if (response.status === 200) {
                            this.tags.items.push(response.data);
                            this.$message.success("添加成功");
                        }
                    })
                    .catch(error => {
                        if (error.response) {
                            this.$message.error(error.response.data.msg);
                        }
                    });
            }
        }
    }
};
</script>

<style scoped>
.v-btn {
    min-width: 36px;
}
</style>
