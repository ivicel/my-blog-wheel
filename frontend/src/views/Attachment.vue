<template>
    <div>
        <v-dialog v-model="dialog" max-width="700">
            <v-card>
                <v-img :src="src" />
            </v-card>
        </v-dialog>
        <v-layout
            justify-center
            align-center
            grow
            column
            v-show="images.length < 1"
            >没有图片</v-layout
        >
        <v-layout wrap justify-space-around>
            <v-hover class="ma-3" v-for="(img, index) in images" :key="img.id">
                <v-card
                    slot-scope="{ hover }"
                    :class="`elevation-${hover ? 12 : 3}`"
                    @click="show(img)"
                >
                    <v-img
                        width="260"
                        height="200"
                        :alt="img.realName"
                        :src="`${img.path}/${img.thumbnailName}`"
                    ></v-img>
                    <v-card-title>
                        {{ img.originalName }}
                    </v-card-title>
                    <v-card-action>
                        <v-spacer />
                        <v-btn
                            class="pa-0"
                            flat
                            color="primary"
                            @click.stop="copyImageLink(img)"
                            >复制链接</v-btn
                        >
                        <v-btn
                            class="pa-0"
                            flat
                            color="red"
                            @click.stop="deleteImage(img.id, index)"
                            >删除</v-btn
                        >
                    </v-card-action>
                </v-card>
            </v-hover>
        </v-layout>
        <v-layout v-show="page.totalPages > 0">
            <v-pagination
                :total-visible="10"
                v-model="page.number"
                @input="fetchImages"
            ></v-pagination>
        </v-layout>
        <v-layout class="upload-wrapper">
            <v-flex>
                <v-card>
                    <v-card-text v-show="files.length > 0" class="upload-list">
                        <v-list>
                            <v-list-tile v-for="(f, i) in files" :key="f.name">
                                <v-list-tile-action>
                                    <v-icon>attachment</v-icon>
                                </v-list-tile-action>
                                <v-list-tile-content>
                                    <v-list-tile-title>{{
                                        f.name
                                    }}</v-list-tile-title>
                                </v-list-tile-content>
                                <v-list-tile-avatar>
                                    <v-icon @click="files.splice(i, 1)"
                                        >close</v-icon
                                    >
                                </v-list-tile-avatar>
                            </v-list-tile>
                        </v-list>
                    </v-card-text>
                    <v-progress-linear
                        v-show="files.length > 0"
                        v-model="progress"
                        color="grey"
                        height="4"
                    />
                    <v-card-actions>
                        <input
                            type="file"
                            ref="fileInput"
                            multiple
                            accept="image/*"
                            @change="onFileChange()"
                            hidden
                        />
                        <v-btn
                            class="white--text blue-grey ligthen-2 mr-2 ml-2"
                            @click="upload()"
                            >上传<v-icon right>cloud_upload</v-icon></v-btn
                        >
                        <v-tooltip top>
                            <template v-slot:activator="{ on }">
                                <v-icon v-on="on" @click="activeFilePrompt()">
                                    attachment
                                </v-icon>
                            </template>
                            <span>选择文件</span>
                        </v-tooltip>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
export default {
    data() {
        return {
            files: [],
            images: [],
            progress: 0,
            page: {
                number: 1,
                pageSize: 10,
                totalPages: 0
            },
            src: "",
            dialog: false
        };
    },
    created() {
        this.fetchImages();
    },
    methods: {
        upload() {
            if (this.files.length < 1) {
                this.$message.warning("没有选择任何文件");
                return;
            }

            let formData = new FormData();
            this.files.forEach(f => formData.append("images", f));
            this.$axios
                .post("/attachment/upload", formData, {
                    onUploadProgress: event => {
                        this.progress =
                            Math.round(event.loaded * 100) / event.total;
                    }
                })
                .then(response => {
                    let result = response.data;
                    this.files = this.files.filter(
                        file => result[file.name] !== undefined
                    );
                    this.fetchImages();
                    this.$message.info("上传完成");
                })
                .catch(error => {
                    if (error.request) {
                        this.$message.error(error.request.statusText);
                    }
                });
        },
        fetchImages() {
            this.$axios
                .get("/attachment", {
                    params: {
                        page: this.page.number - 1,
                        size: this.page.pageSize
                    }
                })
                .then(response => {
                    this.page.totalPages = response.data.totalPages;
                    this.page.number = response.data.number + 1;
                    this.page.pageSize = response.data.size;

                    this.images = response.data.content;
                });
        },
        onFileChange() {
            let files = [];
            Array.prototype.push.apply(files, this.$refs.fileInput.files);
            if (files.length > 0) {
                files = files.filter(
                    file =>
                        this.files.find(f => f.name === file.name) === undefined
                );
                files.forEach(f => this.files.push(f));
            }
        },
        activeFilePrompt() {
            this.$refs.fileInput.click();
        },
        show(img) {
            this.dialog = true;
            this.src = `${img.path}/${img.realName}`;
        },
        deleteImage(id, index) {
            this.$loading.show();
            this.$axios
                .delete(`/attachment/${id}`)
                .then(response => {
                    this.images.splice(index, 1);
                    this.$message.success("删除成功");
                })
                .catch(error => {
                    this.$message.error("删除失败");
                })
                .finally(() => {
                    this.$loading.dismiss();
                });
        },
        copyImageLink(img) {
            this.$copyText(`${img.path}/${img.realName}`);
        }
    }
};
</script>

<style scoped>
.upload-wrapper {
    position: fixed;
    padding: 10px;
    right: 0;
    bottom: 0;
    width: 40%;
    margin-right: 20px;
}
.upload-list {
    overflow-y: scroll;
    max-height: 300px;
}
</style>
