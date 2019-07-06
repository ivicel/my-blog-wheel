<template>
    <v-toolbar app clipped-left>
        <v-toolbar-side-icon @click="toggleDrawer" hidden-lg-and-up>
            <v-icon>menu</v-icon>
        </v-toolbar-side-icon>
    </v-toolbar>
</template>

<script>
export default {
    data() {
        return {};
    },
    methods: {
        toggleDrawer() {
            this.$root.app.$emit("app-drawer-toggle");
        }
    }
};
</script>

<style scoped></style>
