export interface Node {
    id: number;
    lat: number;
    lon: number;
    tags: NodeTag;
}

export interface NodeTag {
    name: string;
    start_date: string;
    tourism: string;
    alt_name: string;
    artist_name: string;
    artwork_type: string;
    description: string;
    historic: string;
    wikidata: string;
}