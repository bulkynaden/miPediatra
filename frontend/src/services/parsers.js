export default function getIdFromLink(link) {
  return parseInt(link.split("/").pop());
}
