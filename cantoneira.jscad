// title      : Coluna N2, N3, N5
// author     : Alessandro
// license    : MIT License
// revision   : 0.001
// tags       : cantoneira, N2, N3, N5
// file       : cantoneira.jscad

function oblongo(line_offset, start_point, end_point, radius) {
   //var o = Array.prototype.slice.call();
   return union(     // -- we have to extrude all, in order to union (we can't mix CAG and CSG)
      linear_extrude({height: 2},     // convex hulled extruded
         hull(circle({r: radius, center: true}).translate([line_offset, start_point, 0]), 
             circle({r: radius, center: true}).translate([line_offset, end_point, 0])))
   );
}

function blank(params) {
    //var o = Array.prototype.slice.call(arguments);
    return cube([params.largura, params.comprimento, params.espessura]);
}

function oblongos(params) {
    
    var o1;

    var line_offset = [];

    OpenJsCad.log("Tipo de Cantoneira escolhida: " + params.largura); 
    
    if (params.largura < 85) {
        /* cantoneira N2, setup da furação */
        line_offset[0] = {line: 10, offset: 9.5};   /* primeira fileira de oblongo */
        line_offset[1] = {line: 30, offset: -10.5}; /* segunda fileira de oblongo */          
    } else if (params.largura < 125) {
        /* cantoneira N3, setup da furação */
        line_offset[0] = {line: 15.5, offset: 9.5};   /* primeira fileira de oblongo */
        line_offset[1] = {line: 50.5, offset: -10.5}; /* segunda fileira de oblongo */        
        line_offset[2] = {line: 70.5, offset: 9.5};   /* terceira fileira de oblongo */
    } else {
        /* cantoneira N5, setup da furação */
        line_offset[0] = {line: 11, offset: 9.5};   /* primeira fileira de oblongo */
        line_offset[1] = {line: 31, offset: -10.5}; /* segunda fileira de oblongo */        
        line_offset[2] = {line: 51, offset: 9.5};   /* terceira fileira de oblongo */        
        line_offset[3] = {line: 95, offset: -10.5}; /* quarta fileira de oblongo */        
        line_offset[4] = {line: 115, offset: 9.5};   /* quinta fileira de oblongo */        
    }

    var count = 0;
    
    var obl =[];
    
    var u;
    
    for (x = 0; x < line_offset.length; x++) {
        if (line_offset[x].offset < 0) {
            count = Math.ceil((params.comprimento - line_offset[x].offset) / params.passo);
        } else {
            count = Math.ceil(params.comprimento / params.passo);
        }
        for (i = 0; i < count; i++) {
            obl[i] = oblongo(line_offset[x].line, 
                             line_offset[x].offset + (i * params.passo), 
                             line_offset[x].offset + ((params.tamanho - params.diametro) + (i * params.passo)), 
                             params.diametro / 2);
        }
        
        if (!u) {
            u = union(obl);
        } else {
            u = u.union(obl);
        }
    }

    return u;
}

function getParameterDefinitions() {
  return [
    /* parametros da cantoneira */
    { 
        name: 'largura', 
        type: 'choice', 
        caption: 'Tipo de cantoneira:', 
        values: [66, 85, 125], 
        captions: ["N2", "N3", "N5"], 
        initial: 85 
    },
    {
        name: 'comprimento', 
        type: 'int', 
        initial: 360,
        caption: "Comprimento da cantoneira (mm):", 
    },
    {
        name: 'espessura', 
        type: 'float', 
        initial: 1.8,
        caption: "Espessura da chapa (mm):", 
    },
    { 
        name: 'cor', 
        type: 'choice', 
        caption: 'Cor:', 
        values: ['blue', 'red', 'orange', 'lime', 'black', 'lightgray', 'darkgray'], 
        captions: ['Azul', 'Vermelho', 'Laranja', 'Limão', 'Preto', 'Cinza Claro', 'Cinza Escuro'], 
        initial: 'orange' 
    },
    /* parametros dos oblongos */
    {
        name: 'tamanho', 
        type: 'float', 
        initial: 30,
        caption: "Comprimento do oblongo (mm):", 
    },
    {
        name: 'diametro', 
        type: 'float', 
        initial: 9,
        caption: "Largura do oblongo (mm):", 
    },
    {
        name: 'passo', 
        type: 'int', 
        initial: 40,
        caption: "Passo do oblongo (mm):", 
    }
  ];
}

function main(params) {

    var blk = blank(params);
    var obl = oblongos(params);
    
    var cantoneira = //color(params.cor, blk)
                difference(color(params.cor, blk), obl)
                .translate([(params.largura / 2) * -1, (params.comprimento / 2) * -1, 0])
                .scale(0.2);

    /* propriedades fisicas */
    var features = cantoneira.getFeatures(["volume", "area"]);
    var volume = features[0];
    var area = features[1];
    
    OpenJsCad.log("CANTONEIRA -> volume: " + Math.ceil(volume) + "; area: " + area);  
    
    features = blk.getFeatures(["volume", "area"]);
    volume = features[0];
    area = features[1];
    
    OpenJsCad.log("BLANK -> volume: " + Math.ceil(volume) + "; area: " + area);
    
    features = obl.getFeatures(["volume", "area"]);
    volume = features[0];
    area = features[1];
    
    OpenJsCad.log("OBLONGO -> volume: " + Math.ceil(volume) + "; area: " + area);

    return cantoneira;
}
