import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import { resolve } from "path";
import VitePluginJson5 from "vite-plugin-json5";

export default defineConfig({
  plugins: [vue(), VitePluginJson5()],

  resolve: {
    alias: {
      "@": resolve(__dirname, "src"),
      "cldr-data": resolve(__dirname, "node_modules/cldr-data"),
    },
  },
  server: {
    open: true,
  },
  build: {
    chunkSizeWarningLimit: 3200,
    rollupOptions: {
      external: [
        "./node_modules/cldr-data/supplemental/weekData.json",
        "./node_modules/cldr-data/supplemental/numberingSystems.json",
        "./node_modules/cldr-data/main/es/ca-gregorian.json",
        "./node_modules/cldr-data/main/es/numbers.json",
        "./node_modules/cldr-data/main/es/timeZoneNames.json",
      ],
    },
  },
});
