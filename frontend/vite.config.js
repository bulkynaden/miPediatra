import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import { resolve } from "path";
import json from "@rollup/plugin-json";

export default defineConfig({
  plugins: [vue(), json()],

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
        "cldr-data/supplemental/weekdata.json",
        "cldr-data/supplemental/numberingSystems.json",
        "cldr-data/main/es/ca-gregorian.json",
        "cldr-data/main/es/numbers.json",
        "cldr-data/main/es/timeZoneNames.json",
      ],
    },
  },
});
