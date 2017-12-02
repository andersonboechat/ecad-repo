CREATE OR REPLACE TRIGGER AUD_VERSAO_TRIGGER 
BEFORE UPDATE OF COD_AUD_VERSAO,COD_VERSAO,CONTEUDO_ANTIGO,CONTEUDO_NOVO,NOM_COLUNA,NOM_LOGIN ON AUD_VERSAO
FOR EACH ROW
DECLARE mensagem VARCHAR2(1000);
BEGIN
    IF :new.COD_AUD_VERSAO <> :old.COD_AUD_VERSAO THEN
        mensagem := mensagem || 'COD_AUD_VERSAO: ' || :old.COD_AUD_VERSAO || ' > ' || :new.COD_AUD_VERSAO || '<br/>';
    END IF;

    IF :new.COD_VERSAO <> :old.COD_VERSAO THEN
        mensagem := mensagem || 'COD_VERSAO: ' || :old.COD_VERSAO || ' > ' || :new.COD_VERSAO || '<br/>';
    END IF;

    IF :new.NOM_COLUNA <> :old.NOM_COLUNA THEN
        mensagem := mensagem || 'NOM_COLUNA: ' || :old.NOM_COLUNA || ' > ' || :new.NOM_COLUNA || '<br/>';
    END IF;

    IF :new.CONTEUDO_ANTIGO <> :old.CONTEUDO_ANTIGO THEN
        mensagem := mensagem || 'CONTEUDO_ANTIGO: ' || :old.CONTEUDO_ANTIGO || ' > ' || :new.CONTEUDO_ANTIGO || '<br/>';
    END IF;

    IF :new.CONTEUDO_NOVO <> :old.CONTEUDO_NOVO THEN
        mensagem := mensagem || 'CONTEUDO_NOVO: ' || :old.CONTEUDO_NOVO || ' > ' || :new.CONTEUDO_NOVO || '<br/>';
    END IF;

    IF :new.NOM_LOGIN <> :old.NOM_LOGIN THEN
        mensagem := mensagem || 'NOM_LOGIN: ' || :old.NOM_LOGIN || ' > ' || :new.NOM_LOGIN || '<br/>';
    END IF;

    IF mensagem IS NOT NULL THEN
        mensagem := '<p>Alteracoes realizadas no registro ' || :old.COD_AUD_VERSAO || '</p>' || mensagem;
        EMAIL.ENVIAR('remetente@ecad.org.br', 'destinatario@ecad.org.br', 'Alteracao na tabela AUD_VERSAO', mensagem, 'HTML', NULL);
    END IF;
    
END;