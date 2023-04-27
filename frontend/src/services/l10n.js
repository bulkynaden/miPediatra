import { L10n, loadCldr } from "@syncfusion/ej2-base";

import * as numberingSystems from "cldr-data/supplemental/numberingSystems.json";
import * as gregorian from "cldr-data/main/es/ca-gregorian.json";
import * as numbers from "cldr-data/main/es/numbers.json";
import * as timeZoneNames from "cldr-data/main/es/timeZoneNames.json";
import * as weekData from "cldr-data/supplemental/weekData.json";

loadCldr(numberingSystems, gregorian, numbers, timeZoneNames, weekData);

L10n.load({
  es: {
    datetimepicker: { today: "Hoy" },
    uploader: {
      Browse: "Examinar",
      dropFilesHint: "...o arrastre sus archivos aquí",
      remove: "Eliminar",
      delete: "Borrar archivo",
      invalidFileType: "El tipo de archivo no está permitido",
      invalidMaxFileSize: "El tamaño del archivo es demasiado grande.",
      totalFiles: "Archivos totales",
      size: "Tamaño",
      invalidSelection: "Archivos no válidos seleccionados",
    },
  },
});
