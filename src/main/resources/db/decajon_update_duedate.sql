UPDATE repertoires
SET card = jsonb_set(card, '{due}',
    to_jsonb(
        (extract(epoch from now()) + random() * (7 * 86400))::bigint
    )::text::jsonb
)
WHERE group_id = 1
AND card->>'due' IS NOT NULL;