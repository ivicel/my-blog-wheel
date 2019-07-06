<template>
    <v-app>
        <app-drawer></app-drawer>
        <app-toolbar></app-toolbar>

        <v-content>
            <v-container grid-list-md>
                <router-view />
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
import AppToolbar from "@/components/AppToolbar";
import AppDrawer from "@/components/AppDrawer";

export default {
    components: {
        "app-drawer": AppDrawer,
        "app-toolbar": AppToolbar
    }
};
</script>

<style lang="css">
@media only screen and (min-width: 960px) {
    .container {
        max-width: 1100px;
    }
}
</style>

