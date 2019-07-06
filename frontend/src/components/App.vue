<template>
    <v-app>
        <v-layout>
            <app-drawer></app-drawer>
            <app-toolbar></app-toolbar>
            <v-content>
                <div id="app-component"></div>
            </v-content>
        </v-layout>
    </v-app>
</template>
<script>
import AppToolbar from "@/components/AppToolbar.vue";
import AppDrawer from "@/components/AppDrawer.vue";

export default {
    components: {
        "app-drawer": AppDrawer,
        "app-toolbar": AppToolbar
    }
};
</script>
